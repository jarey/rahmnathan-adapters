module com.github.rahmnathan.movie.info.omdb {
    requires com.github.rahmnathan.movie.info.api;
    requires json;
    requires java.desktop;
    requires imgscalr.lib;
    requires com.github.rahmnathan.http.client;
    requires java.logging;
    requires guava;
    exports com.github.rahmnathan.localmovies.omdb.info.provider;
}