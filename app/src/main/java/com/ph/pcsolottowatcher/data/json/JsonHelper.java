package com.ph.pcsolottowatcher.data.json;

import android.content.Context;
import android.os.Handler;
import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ph.pcsolottowatcher.data.ExecutorHelper;
import com.ph.pcsolottowatcher.pojos.LottoGameBaseModel;
import com.ph.pcsolottowatcher.pojos.results.local.LocalHistoryModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Singleton
public class JsonHelper {
  private static final String ALL_RESULTS_JSON = "AllResults.json";
  private static final String LOTTO_GAMES_JSON = "LottoGames.json";
  private static final String JSON_FILE = "AllResults.json";

  public static final String PCSOGOV_ARRAY_NAME = "pcsogov_games";
  public static final String LOTTO_ARRAY_NAME = "lotto_games";

  private Context context;
  private Gson gson;
  private Handler handler;
  private ExecutorHelper executor;

  @Inject
  public JsonHelper(Context context, Gson gson, Handler handler, ExecutorHelper executor) {
    this.context = context;
    this.gson = gson;
    this.handler = handler;
    this.executor = executor;
  }

  public void getLottoGamesList(Consumer<List<LottoGameBaseModel>> consumer, String arrayName) {
    Runnable runnable =
        () -> {
          List<LottoGameBaseModel> games = new ArrayList<>();

          try {
            JSONObject json = new JSONObject(getString(LOTTO_GAMES_JSON));
            JSONArray jsonArray = json.getJSONArray(arrayName);

            for (int i = 0; i < jsonArray.length(); i++) {
              JSONObject jsonObject = jsonArray.getJSONObject(i);

              LottoGameBaseModel model = new LottoGameBaseModel();
              model.setHistoryLink(jsonObject.getString("historyLink"));
              model.setName(jsonObject.getString("name"));
              model.setType(jsonObject.getString("type"));
              model.setRegex(jsonObject.getString("regex"));

              if (jsonObject.has("tableNumber")) {
                model.setTableNumber(jsonObject.getInt("tableNumber"));
              }

              games.add(model);
            }

            handler.post(
                () -> {
                  consumer.accept(games);
                });
          } catch (JSONException e) {
          }
        };
    executor.execute(runnable);
  }

  public void getPCSOGOVGamesList(Consumer<List<LottoGameBaseModel>> consumer, String arrayName) {
    Runnable runnable =
        () -> {
          List<LottoGameBaseModel> games = new ArrayList<>();

          try {
            JSONObject json = new JSONObject(getString(LOTTO_GAMES_JSON));
            JSONArray jsonArray = json.getJSONArray(arrayName);

            for (int i = 0; i < jsonArray.length(); i++) {
              JSONObject jsonObject = jsonArray.getJSONObject(i);

              LottoGameBaseModel model = new LottoGameBaseModel();
              model.setName(jsonObject.getString("name"));
              games.add(model);
            }

            handler.post(
                () -> {
                  consumer.accept(games);
                });
          } catch (JSONException e) {
          }
        };
    executor.execute(runnable);
  }

  public void getAllResults(Consumer<List<LocalHistoryModel>> consumer) {
    Runnable runnable =
        () -> {
          String json = getString(JSON_FILE);
          List<LocalHistoryModel> list = new ArrayList<>();
          Type type = new TypeToken<List<LocalHistoryModel>>() {}.getType();

          list = gson.fromJson(json, type);
          consumer.accept(list);
        };
    executor.execute(runnable);
  }

  private String getString(@NonNull String file) {
    String json;

    try (InputStream is = context.getAssets().open(file)) {
      int size = is.available();
      byte[] buffer = new byte[size];
      is.read(buffer);
      json = new String(buffer, "UTF-8");
    } catch (IOException e) {
      return null;
    }

    return json;
  }

  @Deprecated
  public void dataWriter(List<LocalHistoryModel> list) {
    String json = gson.toJson(list);

    try (FileWriter writer = new FileWriter(new File(context.getFilesDir(), JSON_FILE))) {
      writer.write(json);
    } catch (IOException e) {
    }
  }
}
