package pt.ipg.appcarros

class Empresa {


    lateinit var uid: String
    lateinit var nome: String
    lateinit var mail: String
    lateinit var contatotelefone: String
    lateinit var morada: String
    lateinit var localidade: String

    constructor(
        uid: String,
        nome: String,
        mail: String,
        contatotelefone: String,
        morada: String,
        localidade: String
    ) {
        this.uid = uid
        this.nome = nome
        this.mail = mail
        this.contatotelefone = contatotelefone
        this.morada = morada
        this.localidade = localidade
    }
    constructor()
}