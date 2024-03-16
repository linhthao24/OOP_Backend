//package com.oop.web1.configs;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.oop.web1.repositories.MovieRepository;
//import com.oop.web1.entitys.MoviesEntity;
//import com.oop.web1.dto.movieDto.MovieDetailDto;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//
//@Configuration
//@EnableWebSocket
//public class ServerWebSocketConfig implements WebSocketConfigurer {
//    @Autowired
//    private MovieRepository movieRepository;
//
//    private WebSocketSession session;
//    private Timer timer;
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(webSocketHandler(), "/websocket").setAllowedOrigins("*");
//    }
//
//    @Bean
//    public WebSocketHandler webSocketHandler() {
//        return new MyWebSocketHandler();
//    }
//
//    @Component
//    public class MyWebSocketHandler extends TextWebSocketHandler {
//
//        @Override
//        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//            super.afterConnectionEstablished(session);
//            ServerWebSocketConfig.this.session = session;
//            startSendingMovieData();
//        }
//
//        @Override
//        protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//            String payload = message.getPayload();
//            if (payload.equals("exitdata")) {
//                stopSendingMovieData();
//                session.close();
//            }
//        }
//
//        private void startSendingMovieData() {
//            timer = new Timer();
//            timer.scheduleAtFixedRate(new TimerTask() {
//                @Override
//                public void run() {
//                    List<MoviesEntity> movies = movieRepository.findAll();
//                    for (MoviesEntity movie : movies) {
//                        try {
//                            MovieDetailDto movieDetailDto = convertToDto(movie);
//                            String movieDetailDtoJson = new ObjectMapper().writeValueAsString(movieDetailDto);
//                            session.sendMessage(new TextMessage(movieDetailDtoJson));
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }, 0, 30000); // Gửi mỗi 30 giây
//        }
//
//        private void stopSendingMovieData() {
//            timer.cancel();
//        }
//
//        private MovieDetailDto convertToDto(MoviesEntity moviesEntity) {
//            MovieDetailDto movieDetailDto = new MovieDetailDto();
//            // Set thông tin từ entity sang DTO
//            movieDetailDto.setMovieId(moviesEntity.getMovieId());
//            movieDetailDto.setName(moviesEntity.getName());
//            movieDetailDto.setDescription(moviesEntity.getDescription());
//            movieDetailDto.setDirector(moviesEntity.getDirector());
//            movieDetailDto.setActor(moviesEntity.getActor());
//            movieDetailDto.setGenre(moviesEntity.getGenre());
//            //movieDetailDto.setReleaseDate(moviesEntity.getReleaseDate());
//            movieDetailDto.setRunningTime(moviesEntity.getRunningTime());
//            movieDetailDto.setRating(moviesEntity.getRating());
//            movieDetailDto.setPosterUrl(moviesEntity.getPosterUrl());
//            movieDetailDto.setTrailerUrl(moviesEntity.getTrailerUrl());
//            // Các trường khác cần thiết
//            return movieDetailDto;
//        }
//    }
//}
