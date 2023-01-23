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

/*
* This fragment shows the progress of the selected profile
*
* ------------------------------------
* @authors: Tomás Jorge, Luiz Felhberg
* @numbers: 20436, 20347
*/
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

    /**
     * This method is a make sure that view is fully created and its purpose is show the progress
     * of the selected profile, with the information of his own progress
     *
     * @param view – The View returned by onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState – If non-null, this fragment is being re-constructed from a
     * previous saved state as given here.
     * */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        generateMenu()

        setTextViewToProfileName()
        fillCheckboxes()
        // Listener to cancel the user changed and pop the users back to the list of the profiles
        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        // Listener to update the progress of profile depending on the value of the checkboxes
        binding.btnConfirm.setOnClickListener {
            lifecycleScope.launch {
                progressViewModel.updateProgress(getCheckboxChecks())
                setProfileProgressStage()
                setProfileProgress()
            }
            Snackbar.make(view, getString(R.string.snackbar_progress_save), Snackbar.LENGTH_SHORT)
                .show()
            findNavController().popBackStack()
        }
    }

    /**
     * This method changes the TextView to the name of the selected profile
     * */
    private fun setTextViewToProfileName() {
        val name = profileViewModel.getProfileNameById(args.profileID)
        binding.profileProgressName.text = name
    }

    /**
     * This method fill the checkboxes depending on the progress of the selected progress
     * */
    private fun fillCheckboxes() {
        lifecycleScope.launch {
            val progressList = progressViewModel.getAllFromProfileId(args.profileID)
            binding.progressCheckboxAfetivoRelacionamento.isChecked = progressList.relacionamento
            binding.progressCheckboxAfetivoEqEmocional.isChecked = progressList.eqEmocional
            binding.progressCheckboxAfetivoAutoestima.isChecked = progressList.autoestima

            binding.progressCheckboxCaracterAutonomia.isChecked = progressList.autonomia
            binding.progressCheckboxCaracterResponsabilidade.isChecked = progressList.responsabilidade
            binding.progressCheckboxCaracterCoerencia.isChecked = progressList.coerencia

            binding.progressCheckboxEspiritualDescoberta.isChecked = progressList.descoberta
            binding.progressCheckboxEspiritualAprofundamento.isChecked = progressList.aprofundamento
            binding.progressCheckboxEspiritualServico.isChecked = progressList.servico

            binding.progressCheckboxFisicolAutoconhecimento.isChecked = progressList.autoconhecimento
            binding.progressCheckboxFisicoBemestarFisico.isChecked = progressList.bemestarFisico
            binding.progressCheckboxFisicoDesempenho.isChecked = progressList.desempenho

            binding.progressCheckboxIntelectualConhecimento.isChecked = progressList.procuraConhecimento
            binding.progressCheckboxIntelectualResProblemas.isChecked = progressList.resolucaoProblemas
            binding.progressCheckboxIntelectualCriatividade.isChecked = progressList.criatividade

            binding.progressCheckboxSocialCidadania.isChecked = progressList.exercerCidadania
            binding.progressCheckboxSocialSolidariedade.isChecked = progressList.solidariedade
            binding.progressCheckboxSocialInteracao.isChecked = progressList.interecao
        }
    }

    /**
     * Method used to get an object of [Progress] to update the progress of the profile
     *
     * @return  [Progress] with the respective value of the checkboxes
     * */
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

    /**
     * Method that changes the stage of the profile according to the tracks it has made
     * */
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

    /**
     * Method that changes the progress of the profile according to its section and tracks
     * */
    private fun setProfileProgress() {
        val profileSection = profileViewModel.getProfileSection(args.profileID)
        if (profileSection == getString(R.string.section_cub_scouts)) clubScoutsProgress()
        if (profileSection == getString(R.string.section_scouts)) scoutsSectionProgress()
        if (profileSection == getString(R.string.section_venture_scout)) ventureScoutsProgress()
        if (profileSection == getString(R.string.section_rovers)) roversProgress()
    }

    //TODO
    /**
     * Method that is called if the profile is [R.string.section_cub_scouts] and changes the progress of the
     * profile according to the tracks made
     * */
    private fun clubScoutsProgress() {
        val stageOne = profileProgressViewModel.getStageOneValue(args.profileID)
        val stageTwo = profileProgressViewModel.getStageTwoValue(args.profileID)
        val stageThree = profileProgressViewModel.getStageThreeValue(args.profileID)
        if (stageOne)
            profileViewModel.setProfileProgressName(
                args.profileID,
                getString(R.string.profile_progress_cs_stage_one)
            )
        else
            profileViewModel.setProfileProgressName(
                args.profileID,
                getString(R.string.profile_progress_cs_no_stage)
            )
        if (stageTwo)
            profileViewModel.setProfileProgressName(
                args.profileID,
                getString(R.string.profile_progress_cs_stage_two)
            )
        if (stageThree)
            profileViewModel.setProfileProgressName(
                args.profileID,
                getString(R.string.profile_progress_cs_stage_three)
            )
    }

    /**
     * Method that is called if the profile is [R.string.section_scouts] and changes the progress of the
     * profile according to the tracks made
     * */
    private fun scoutsSectionProgress() {
        val stageOne = profileProgressViewModel.getStageOneValue(args.profileID)
        val stageTwo = profileProgressViewModel.getStageTwoValue(args.profileID)
        val stageThree = profileProgressViewModel.getStageThreeValue(args.profileID)
        if (stageOne)
            profileViewModel.setProfileProgressName(
                args.profileID,
                getString(R.string.profile_progress_scouts_stage_one)
            )
        else
            profileViewModel.setProfileProgressName(
                args.profileID,
                getString(R.string.profile_progress_scouts_no_stage)
            )
        if (stageTwo)
            profileViewModel.setProfileProgressName(
                args.profileID,
                getString(R.string.profile_progress_scouts_stage_two)
            )
        if (stageThree)
            profileViewModel.setProfileProgressName(
                args.profileID,
                getString(R.string.profile_progress_scouts_stage_three)
            )
    }

    /**
     * Method that is called if the profile is [R.string.section_venture_scout] and changes the progress of the
     * profile according to the tracks made
     * */
    private fun ventureScoutsProgress() {
        val stageOne = profileProgressViewModel.getStageOneValue(args.profileID)
        val stageTwo = profileProgressViewModel.getStageTwoValue(args.profileID)
        val stageThree = profileProgressViewModel.getStageThreeValue(args.profileID)
        if (stageOne)
            profileViewModel.setProfileProgressName(
                args.profileID,
                getString(R.string.profile_progress_vs_stage_one)
            )
        else
            profileViewModel.setProfileProgressName(
                args.profileID,
                getString(R.string.profile_progress_vs_no_stage)
            )
        if (stageTwo)
            profileViewModel.setProfileProgressName(
                args.profileID,
                getString(R.string.profile_progress_vs_stage_two)
            )
        if (stageThree)
            profileViewModel.setProfileProgressName(
                args.profileID,
                getString(R.string.profile_progress_vs_stage_three)
            )
    }

    /**
     * Method that is called if the profile is [R.string.section_rovers] and changes the progress of the
     * profile according to the tracks made
     * */
    private fun roversProgress() {
        val stageOne = profileProgressViewModel.getStageOneValue(args.profileID)
        val stageTwo = profileProgressViewModel.getStageTwoValue(args.profileID)
        val stageThree = profileProgressViewModel.getStageThreeValue(args.profileID)
        if (stageOne)
            profileViewModel.setProfileProgressName(
                args.profileID,
                getString(R.string.profile_progress_rovers_stage_one)
            )
        else
            profileViewModel.setProfileProgressName(
                args.profileID,
                getString(R.string.profile_progress_rovers_no_stage)
            )
        if (stageTwo)
            profileViewModel.setProfileProgressName(
                args.profileID,
                getString(R.string.profile_progress_rovers_stage_two)
            )
        if (stageThree)
            profileViewModel.setProfileProgressName(
                args.profileID,
                getString(R.string.profile_progress_rovers_stage_three)
            )
    }

    /**
     * Method that counts how many tracks the profile has made in the affective area
     * */
    private fun countTrueAffective(): Int {
        var count = 0
        if (binding.progressCheckboxAfetivoRelacionamento.isChecked) count++
        if (binding.progressCheckboxAfetivoEqEmocional.isChecked) count++
        if (binding.progressCheckboxAfetivoAutoestima.isChecked) count++
        return count
    }

    /**
     * Method that counts how many tracks the profile has made in the character area
     * */
    private fun countTrueCharacter(): Int {
        var count = 0
        if (binding.progressCheckboxCaracterAutonomia.isChecked) count++
        if (binding.progressCheckboxCaracterResponsabilidade.isChecked) count++
        if (binding.progressCheckboxCaracterCoerencia.isChecked) count++
        return count
    }

    /**
     * Method that counts how many tracks the profile has made in the spiritual area
     * */
    private fun countTrueSpiritual(): Int {
        var count = 0
        if (binding.progressCheckboxEspiritualDescoberta.isChecked) count++
        if (binding.progressCheckboxEspiritualAprofundamento.isChecked) count++
        if (binding.progressCheckboxEspiritualServico.isChecked) count++
        return count
    }

    /**
     * Method that counts how many tracks the profile has made in the physical area
     * */
    private fun countTruePhysical(): Int {
        var count = 0
        if (binding.progressCheckboxFisicoDesempenho.isChecked) count++
        if (binding.progressCheckboxFisicolAutoconhecimento.isChecked) count++
        if (binding.progressCheckboxFisicoBemestarFisico.isChecked) count++
        return count
    }

    /**
     * Method that counts how many tracks the profile has made in the intellectual area
     * */
    private fun countTrueIntellectual(): Int {
        var count = 0
        if (binding.progressCheckboxIntelectualConhecimento.isChecked) count++
        if (binding.progressCheckboxIntelectualResProblemas.isChecked) count++
        if (binding.progressCheckboxIntelectualCriatividade.isChecked) count++
        return count
    }

    /**
     * Method that counts how many tracks the profile has made in the social area
     * */
    private fun countTrueSocial(): Int {
        var count = 0
        if (binding.progressCheckboxSocialCidadania.isChecked) count++
        if (binding.progressCheckboxSocialSolidariedade.isChecked) count++
        if (binding.progressCheckboxSocialInteracao.isChecked) count++
        return count
    }

    /**
     * Method that creates an option in the app menu to go to the main menu
     */
    private fun generateMenu() {
        val menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.home -> {
                        findNavController().navigate(ProfileProgressFragmentDirections.actionProfileProgressFragmentToMainMenuFragment())
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}