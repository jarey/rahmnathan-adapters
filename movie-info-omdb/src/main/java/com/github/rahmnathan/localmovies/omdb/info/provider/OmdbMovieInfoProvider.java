package com.github.rahmnathan.localmovies.omdb.info.provider;

import com.github.rahmnathan.movie.info.api.IMovieInfoProvider;
import com.github.rahmnathan.movie.info.data.MovieInfo;
import org.imgscalr.Scalr;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class OmdbMovieInfoProvider implements IMovieInfoProvider {
    private final OmdbRawDataProvider dataProvider;
    private final MovieInfoMapper movieInfoMapper = new MovieInfoMapper();

    public OmdbMovieInfoProvider(String apiKey){
        this.dataProvider = new OmdbRawDataProvider(apiKey);
    }

    @Override
    public MovieInfo loadMovieInfo(String title){
        String fileName = title;
        if(title.charAt(title.length() - 4) == '.')
            title = title.substring(0, title.length()-4);

        JSONObject jsonMovieInfo = dataProvider.loadMovieInfo(title);
        byte[] poster = loadPoster(jsonMovieInfo);
        return movieInfoMapper.jsonToMovieInfo(jsonMovieInfo, poster, fileName);
    }

    private byte[] loadPoster(JSONObject jsonMovieInfo) {
        try {
            URL url = new URL(jsonMovieInfo.get("Poster").toString());
            return scaleImage(dataProvider.loadMoviePoster(url));
        } catch (MalformedURLException | JSONException e) {
            return new byte[0];
        }
    }

    private byte[] scaleImage(byte[] poster) {
        if (poster.length == 0)
            return poster;

        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(poster));
            bufferedImage = Scalr.resize(bufferedImage, Scalr.Method.ULTRA_QUALITY, 200);
            ImageIO.write(bufferedImage, "jpg", outputStream);
            outputStream.flush();
            return outputStream.toByteArray();
        } catch (IOException e){
            return new byte[0];
        }
    }
}
