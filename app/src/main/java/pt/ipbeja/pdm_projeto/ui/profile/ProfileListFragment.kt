package pt.ipbeja.pdm_projeto.ui.profile

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import pt.ipbeja.pdm_projeto.R
import pt.ipbeja.pdm_projeto.databinding.FragmentProfileListBinding
import pt.ipbeja.pdm_projeto.databinding.ProfileItemBinding
import pt.ipbeja.pdm_projeto.db.Profile
import pt.ipbeja.pdm_projeto.viewmodel.ProfileViewModel
import java.io.File


class ProfileListFragment : Fragment() {

    private lateinit var binding: FragmentProfileListBinding
    private val args: ProfileListFragmentArgs by navArgs()//FragmentBArgs by navArgs<FragmentBArgs>()
    private val adapter = ProfileAdapter()
    private val viewModel: ProfileViewModel by viewModels()

    companion object {
        private const val LIST_ITEM_1 = "Editar Perfil"
        private const val LIST_ITEM_2 = "Eliminar Perfil"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        generateMenu()

        when (args.section) {
            1 -> {
                binding.section.text = "Lobitos"
                view.setBackgroundResource(R.color.yellow)
            }
            2 -> {
                binding.section.text = "Exploradores"
                view.setBackgroundResource(R.color.green)
            }
            3 -> {
                binding.section.text = "Pioneiros"
                view.setBackgroundResource(R.color.blue)
            }
            4 -> {
                binding.section.text = "Caminheiros"
                view.setBackgroundResource(R.color.red)
            }
        }

        if (args.progressDone) {
            adapter.setData(viewModel.getProfileListFromSectionAndProgressDone(binding.section.text.toString()))
            binding.profileList.adapter = adapter
        }
        else {
            adapter.setData(viewModel.getProfileListFromSection(binding.section.text.toString()))
            binding.profileList.adapter = adapter
        }

        binding.profileBack.setOnClickListener {
            findNavController().navigate(ProfileListFragmentDirections.actionProfileListFragmentToChooseSectionFragment(false))
        }
    }

    inner class ProfileViewHolder(private val binding: ProfileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var profile: Profile

        init {
            binding.root.setOnLongClickListener {
                setOnLongClickListener(it, profile, adapterPosition)
                true
            }

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
            binding.profileProgressName.text = profile.progressName
            val imgBitmap = BitmapFactory.decodeFile(File(profile.picturePath).absolutePath)
            binding.profilePicture.setImageBitmap(imgBitmap)
        }
    }

    inner class ProfileAdapter(profileList: List<Profile> = mutableListOf()) :
        RecyclerView.Adapter<ProfileViewHolder>() {

        private val data: MutableList<Profile> = mutableListOf()

        init {
            data.addAll(profileList)
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
    }

    private fun setOnLongClickListener(view: View, profile: Profile, adapterPosition: Int) {
        val listItems = arrayOf(LIST_ITEM_1, LIST_ITEM_2)
        AlertDialog.Builder(requireContext())
            .setTitle("Escolha uma Opção")
            .setSingleChoiceItems(listItems, -1, ) { dialog, which ->
                if (listItems[which] == LIST_ITEM_1) {
                    findNavController().navigate(ProfileListFragmentDirections.actionProfileListFragmentToCreateProfileFragment(profile.id))
                }
                if (listItems[which] == LIST_ITEM_2) {
                    confirmDelete(view, profile, adapterPosition)
                }
                println(listItems[which])
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun confirmDelete(view: View, profile: Profile, adapterPosition: Int) {
        Snackbar.make(view, "'${profile.name}' has been deleted.", Snackbar.LENGTH_SHORT).show()
        viewModel.deleteProfile(profile)
        adapter.remove(adapterPosition)
    }

    private fun generateMenu() {
        val menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.home -> {
                        findNavController().navigate(ProfileListFragmentDirections.actionProfileListFragmentToMainMenuFragment())
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


}