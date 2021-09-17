package pt.ipg.appcarros

class Carro {

    lateinit var id: String
    lateinit var marca: String
    lateinit var kilometro: String
    lateinit var matricula: String
    lateinit var ano: String



    constructor()
    constructor(id: String, marca: String, kilometro: String, matricula: String, ano: String) {
        this.id = id
        this.marca = marca
        this.kilometro = kilometro
        this.matricula = matricula
        this.ano = ano
    }
}