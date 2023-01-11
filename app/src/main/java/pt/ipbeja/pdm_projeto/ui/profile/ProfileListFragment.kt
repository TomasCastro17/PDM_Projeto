package pt.ipbeja.pdm_projeto.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import pt.ipbeja.pdm_projeto.databinding.FragmentProfileListBinding
import pt.ipbeja.pdm_projeto.databinding.ProfileItemBinding
import pt.ipbeja.pdm_projeto.db.Profile
import pt.ipbeja.pdm_projeto.ui.ProfileViewModel


class ProfileListFragment : Fragment() {

    private lateinit var binding: FragmentProfileListBinding
    private val args: ProfileListFragmentArgs by navArgs<ProfileListFragmentArgs>()//FragmentBArgs by navArgs<FragmentBArgs>()
    private val adapter = ProfileAdapter()
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (args.section) {
            1 -> binding.section.text = "Lobitos"
            2 -> binding.section.text = "Exploradores"
            3 -> binding.section.text = "Pioneiros"
            4 -> binding.section.text = "Caminheiros"
        }

//        val profileList = viewModel.profileList
        val profileList = viewModel.getProfileListFromSection(binding.section.text.toString())
        adapter.setData(profileList)
        binding.profileList.adapter = adapter
    }

    inner class ProfileViewHolder(private val binding: ProfileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var profile: Profile

        init {
            /* binding.root.setOnLongClickListener {

                 Snackbar.make(it, "'${profile.name}' has been deleted.", Snackbar.LENGTH_SHORT).show()
                 ScoutsDB(requireContext()).profileDao().delete(profile)
                 adapter.remove(adapterPosition)
                 true // devolvemos true se tratamos deste evento
             }*/

            binding.root.setOnClickListener {
                findNavController().navigate(
                    ProfileListFragmentDirections.actionProfileListFragmentToProfileProgressFragment(
                        profile.id
                    )
                )
            }
        }

        fun bind(profile: Profile) {
            this.profile = profile
            binding.profileName.text = profile.name
            binding.profilePicture.setImageURI(profile.picturePath.toUri())
        }
    }

    inner class ProfileAdapter(profileList: List<Profile> = mutableListOf()) :
        RecyclerView.Adapter<ProfileViewHolder>() {

        private val data: MutableList<Profile> = mutableListOf()

        init {
            data.addAll(profileList)
        }

        fun add(profile: Profile) {
            data.add(profile)
            notifyItemInserted(data.lastIndex)
        }

        fun remove(position: Int) {
            data.removeAt(position)
            notifyItemRemoved(position)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ProfileItemBinding.inflate(layoutInflater, parent, false)
            return ProfileViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
            val contact = data[position]
            holder.bind(contact)
        }

        override fun getItemCount() = data.size

        fun setData(profileList: List<Profile>) {
            data.clear()
            data.addAll(profileList)

        }

        fun clear() {
            notifyItemRangeRemoved(0, data.size)
            data.clear()
        }

    }

}