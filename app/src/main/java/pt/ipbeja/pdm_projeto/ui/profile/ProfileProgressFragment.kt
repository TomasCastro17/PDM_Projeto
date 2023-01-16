package pt.ipbeja.pdm_projeto.ui.profile

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import pt.ipbeja.pdm_projeto.R
import pt.ipbeja.pdm_projeto.databinding.FragmentProfileProgressBinding
import pt.ipbeja.pdm_projeto.db.Progress
import pt.ipbeja.pdm_projeto.viewmodel.ProfileProgressViewModel
import pt.ipbeja.pdm_projeto.viewmodel.ProfileViewModel
import pt.ipbeja.pdm_projeto.viewmodel.ProgressViewModel

class ProfileProgressFragment : Fragment() {

    private lateinit var binding: FragmentProfileProgressBinding
    private val args: ProfileProgressFragmentArgs by navArgs()
    private val progressViewModel: ProgressViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private val profileProgressViewModel: ProfileProgressViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileProgressBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        generateMenu()

        setTextViewToProfileName()
        fillCheckboxes()
        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnConfirm.setOnClickListener {
            lifecycleScope.launch {
                progressViewModel.updateProgress(getCheckboxChecks())
                setProfileProgressStage()
                setProfileProgress()
            }
            Snackbar.make(view, "Progresso guardado com sucesso!!", Snackbar.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    private fun setTextViewToProfileName() {
        val name = profileViewModel.getProfileNameById(args.profileID)
        binding.profileProgressName.text = name
    }

    private fun fillCheckboxes() {
        lifecycleScope.launch {
            val progressList = progressViewModel.getAllFromProfileId(args.profileID).toString()
            val attributes = progressList.split("(")[1].split(")")[0].split(",")

            binding.progressCheckboxAfetivoRelacionamento.isChecked =
                attributes[0].split("=")[1].toBoolean()
            binding.progressCheckboxAfetivoEqEmocional.isChecked =
                attributes[1].split("=")[1].toBoolean()
            binding.progressCheckboxAfetivoAutoestima.isChecked =
                attributes[2].split("=")[1].toBoolean()

            binding.progressCheckboxCaracterAutonomia.isChecked =
                attributes[3].split("=")[1].toBoolean()
            binding.progressCheckboxCaracterResponsabilidade.isChecked =
                attributes[4].split("=")[1].toBoolean()
            binding.progressCheckboxCaracterCoerencia.isChecked =
                attributes[5].split("=")[1].toBoolean()

            binding.progressCheckboxEspiritualDescoberta.isChecked =
                attributes[6].split("=")[1].toBoolean()
            binding.progressCheckboxEspiritualAprofundamento.isChecked =
                attributes[7].split("=")[1].toBoolean()
            binding.progressCheckboxEspiritualServico.isChecked =
                attributes[8].split("=")[1].toBoolean()

            binding.progressCheckboxFisicoDesempenho.isChecked =
                attributes[9].split("=")[1].toBoolean()
            binding.progressCheckboxFisicolAutoconhecimento.isChecked =
                attributes[10].split("=")[1].toBoolean()
            binding.progressCheckboxFisicoBemestarFisico.isChecked =
                attributes[11].split("=")[1].toBoolean()

            binding.progressCheckboxIntelectualConhecimento.isChecked =
                attributes[12].split("=")[1].toBoolean()
            binding.progressCheckboxIntelectualResProblemas.isChecked =
                attributes[13].split("=")[1].toBoolean()
            binding.progressCheckboxIntelectualCriatividade.isChecked =
                attributes[14].split("=")[1].toBoolean()

            binding.progressCheckboxSocialCidadania.isChecked =
                attributes[15].split("=")[1].toBoolean()
            binding.progressCheckboxSocialSolidariedade.isChecked =
                attributes[16].split("=")[1].toBoolean()
            binding.progressCheckboxSocialInteracao.isChecked =
                attributes[17].split("=")[1].toBoolean()
        }
    }

    private fun getCheckboxChecks(): Progress {
        val progressId = progressViewModel.getProgressIdFromProfileId(args.profileID)
        return Progress(
            relacionamento = binding.progressCheckboxAfetivoRelacionamento.isChecked,
            eqEmocional = binding.progressCheckboxAfetivoEqEmocional.isChecked,
            autoestima = binding.progressCheckboxAfetivoAutoestima.isChecked,
            autonomia = binding.progressCheckboxCaracterAutonomia.isChecked,
            responsabilidade = binding.progressCheckboxCaracterResponsabilidade.isChecked,
            coerencia = binding.progressCheckboxCaracterCoerencia.isChecked,
            descoberta = binding.progressCheckboxEspiritualDescoberta.isChecked,
            aprofundamento = binding.progressCheckboxEspiritualAprofundamento.isChecked,
            servico = binding.progressCheckboxEspiritualServico.isChecked,
            desempenho = binding.progressCheckboxFisicoDesempenho.isChecked,
            autoconhecimento = binding.progressCheckboxFisicolAutoconhecimento.isChecked,
            bemestarFisico = binding.progressCheckboxFisicoBemestarFisico.isChecked,
            procuraConhecimento = binding.progressCheckboxIntelectualConhecimento.isChecked,
            resolucaoProblemas = binding.progressCheckboxIntelectualResProblemas.isChecked,
            criatividade = binding.progressCheckboxIntelectualCriatividade.isChecked,
            exercerCidadania = binding.progressCheckboxSocialCidadania.isChecked,
            solidariedade = binding.progressCheckboxSocialSolidariedade.isChecked,
            interecao = binding.progressCheckboxSocialInteracao.isChecked,
            progressId = progressId
        )
    }

    private fun setProfileProgressStage() {
        val progressId = progressViewModel.getProgressIdFromProfileId(args.profileID)
        if (countTrueAffective() >= 1 && countTrueCharacter() >= 1 && countTrueSpiritual() >= 1 &&
            countTruePhysical() >= 1 && countTrueIntellectual() >= 1 && countTrueSocial() >= 1
        ) {
            profileProgressViewModel.setStageOneTrue(args.profileID, progressId, true)
        } else {
            profileProgressViewModel.setStageOneTrue(args.profileID, progressId, false)
        }
        if (countTrueAffective() >= 2 && countTrueCharacter() >= 2 && countTrueSpiritual() >= 2 &&
            countTruePhysical() >= 2 && countTrueIntellectual() >= 2 && countTrueSocial() >= 2
        ) {
            profileProgressViewModel.setStageTwoTrue(args.profileID, progressId, true)
        } else {
            profileProgressViewModel.setStageTwoTrue(args.profileID, progressId, false)
        }
        if (countTrueAffective() == 3 && countTrueCharacter() == 3 && countTrueSpiritual() == 3 &&
            countTruePhysical() == 3 && countTrueIntellectual() == 3 && countTrueSocial() == 3
        ) {
            profileProgressViewModel.setStageThreeTrue(args.profileID, progressId, true)
            profileProgressViewModel.setProgressDone(args.profileID, progressId, true)
        } else {
            profileProgressViewModel.setStageThreeTrue(args.profileID, progressId, false)
            profileProgressViewModel.setProgressDone(args.profileID, progressId, false)
        }
    }

    private fun setProfileProgress() {
        val profileSection = profileViewModel.getProfileSection(args.profileID)
        println(profileSection)
        println(profileSection)
        println(profileSection)
        println(profileSection)
        if(profileSection == "Lobitos") lobitosProgress()
        if(profileSection == "Exploradores") exploradoresProgress()
        if(profileSection == "Pioneiros") pioneirosProgress()
        if(profileSection == "Caminheiros") caminheirosProgress()
    }

    private fun lobitosProgress() {
        val stageOne = profileProgressViewModel.getStageOneValue(args.profileID)
        val stageTwo = profileProgressViewModel.getStageTwoValue(args.profileID)
        val stageThree = profileProgressViewModel.getStageThreeValue(args.profileID)
        if(stageOne)  profileViewModel.setProfileProgressName(args.profileID, "Lobo Valente")
        else profileViewModel.setProfileProgressName(args.profileID, "Pata Tenra")
        if(stageTwo)  profileViewModel.setProfileProgressName(args.profileID, "Lobo Cortês")
        if(stageThree)  profileViewModel.setProfileProgressName(args.profileID, "Lobo Amigo")
    }

    private fun exploradoresProgress() {
        val stageOne = profileProgressViewModel.getStageOneValue(args.profileID)
        val stageTwo = profileProgressViewModel.getStageTwoValue(args.profileID)
        val stageThree = profileProgressViewModel.getStageThreeValue(args.profileID)
        if(stageOne)  profileViewModel.setProfileProgressName(args.profileID, "Aliança")
        else profileViewModel.setProfileProgressName(args.profileID, "Apelo")
        if(stageTwo)  profileViewModel.setProfileProgressName(args.profileID, "Rumo")
        if(stageThree)  profileViewModel.setProfileProgressName(args.profileID, "Descoberta")
    }

    private fun pioneirosProgress() {
        val stageOne = profileProgressViewModel.getStageOneValue(args.profileID)
        val stageTwo = profileProgressViewModel.getStageTwoValue(args.profileID)
        val stageThree = profileProgressViewModel.getStageThreeValue(args.profileID)
        if(stageOne)  profileViewModel.setProfileProgressName(args.profileID, "Conhecimento")
        else profileViewModel.setProfileProgressName(args.profileID, "Desprendimento")
        if(stageTwo)  profileViewModel.setProfileProgressName(args.profileID, "Vontade")
        if(stageThree)  profileViewModel.setProfileProgressName(args.profileID, "Construção")
    }

    private fun caminheirosProgress() {
        val stageOne = profileProgressViewModel.getStageOneValue(args.profileID)
        val stageTwo = profileProgressViewModel.getStageTwoValue(args.profileID)
        val stageThree = profileProgressViewModel.getStageThreeValue(args.profileID)
        if(stageOne)  profileViewModel.setProfileProgressName(args.profileID, "Comunidade")
        else profileViewModel.setProfileProgressName(args.profileID, "Caminho")
        if(stageTwo)  profileViewModel.setProfileProgressName(args.profileID, "Serviço")
        if(stageThree)  profileViewModel.setProfileProgressName(args.profileID, "Partida")
    }

    private fun countTrueAffective(): Int {
        var count = 0
        if (binding.progressCheckboxAfetivoRelacionamento.isChecked) count++
        if (binding.progressCheckboxAfetivoEqEmocional.isChecked) count++
        if (binding.progressCheckboxAfetivoAutoestima.isChecked) count++
        return count
    }

    private fun countTrueCharacter(): Int {
        var count = 0
        if (binding.progressCheckboxCaracterAutonomia.isChecked) count++
        if (binding.progressCheckboxCaracterResponsabilidade.isChecked) count++
        if (binding.progressCheckboxCaracterCoerencia.isChecked) count++
        return count
    }

    private fun countTrueSpiritual(): Int {
        var count = 0
        if (binding.progressCheckboxEspiritualDescoberta.isChecked) count++
        if (binding.progressCheckboxEspiritualAprofundamento.isChecked) count++
        if (binding.progressCheckboxEspiritualServico.isChecked) count++
        return count
    }

    private fun countTruePhysical(): Int {
        var count = 0
        if (binding.progressCheckboxFisicoDesempenho.isChecked) count++
        if (binding.progressCheckboxFisicolAutoconhecimento.isChecked) count++
        if (binding.progressCheckboxFisicoBemestarFisico.isChecked) count++
        return count
    }

    private fun countTrueIntellectual(): Int {
        var count = 0
        if (binding.progressCheckboxIntelectualConhecimento.isChecked) count++
        if (binding.progressCheckboxIntelectualResProblemas.isChecked) count++
        if (binding.progressCheckboxIntelectualCriatividade.isChecked) count++
        return count
    }

    private fun countTrueSocial(): Int {
        var count = 0
        if (binding.progressCheckboxSocialCidadania.isChecked) count++
        if (binding.progressCheckboxSocialSolidariedade.isChecked) count++
        if (binding.progressCheckboxSocialInteracao.isChecked) count++
        return count
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
                        findNavController().navigate(CreateProfileFragmentDirections.actionCreateProfileFragmentToMainMenuFragment())
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}