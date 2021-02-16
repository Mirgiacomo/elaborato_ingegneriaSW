package elaborato_ingegneriaSW.models;

public enum CausaDecesso {
    INCIDENTE_STRADALE("INCIDENTE STRADALE"),
    MALATTIA_TUMORALE("MALATTIA TUMORALE"),
    MALATTIA_CARDIOVASCOLARE("MALATTIA CARDIOVASCOLARE"),
    MALATTIA_CONTAGIOSA("MALATTIA CONTAGIOSA");

    private final String nome;

    CausaDecesso(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }
}
