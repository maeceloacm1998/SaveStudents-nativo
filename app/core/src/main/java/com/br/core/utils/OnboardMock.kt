package br.oficial.savestudents.utils

import com.br.core.R
import com.example.data_transfer.model.OnboardModel

class OnboardMock {
    companion object {
        fun getOnboardItems(): List<OnboardModel> {
            return listOf(
                OnboardModel(
                    title = "Bem vindo ao Save Students",
                    description = "Esse aplicativo foi desenvolvido\n" + "para te ajudar no acesso da grade \n" + "curricular de qualquer matéria",
                    image = R.drawable.onboard1
                ),
                OnboardModel(
                    title = "Como achar minha matéria",
                    description = "No inicio do aplicativo, você tem acesso a todas matérias cadastradas no aplicativo.",
                    image =  R.drawable.onboard2
                ),
                OnboardModel(
                    title = "Facilitando em encontrar sua matéria",
                    description = "Clicando no botão de filtrar, você consegue selecionar o período ou turno. Além disso, caso saiba o nome da matéria, basta digitar na busca",
                    image =  R.drawable.onboard3
                ),
                OnboardModel(
                    title = "Tenha acesso ao cronograma da sua matéria",
                    description = "Dentro da matéria cadastrada, você tem acesso cronograma,  listando através de uma legenda, todas as aulas, feriados e provas",
                    image =  R.drawable.onboard4
                ),
                OnboardModel(
                    title = "Ative o lembrete da Aula",
                    description = "Dentro de cada matéria, na engrenagem, ative as notificações para receber no seu celular sempre que tiver aula, um lembrete.",
                    image =  R.drawable.onboard5
                ),
            )
        }
    }
}