package br.com.infnet.atividades.services;

import br.com.infnet.atividades.models.Atividades;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Magno on 16/11/2017.
 */

public interface AtividadesService {
    public static final String BASE_URL = "http://infnet.educacao.ws/";

    @GET("dadosAtividades.php")
    Call<Atividades> atividades();
}
