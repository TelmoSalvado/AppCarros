package pt.ipg.appcarros

class Mecanico {

    lateinit var id: String
    lateinit var nome: String
    lateinit var kilometros: String
    lateinit var descricao: String
    lateinit var preco: String
    constructor()
    constructor(id: String, nome: String, kilometros: String, descricao: String, preco: String) {
        this.id = id
        this.nome = nome
        this.kilometros = kilometros
        this.descricao = descricao
        this.preco = preco
    }
}