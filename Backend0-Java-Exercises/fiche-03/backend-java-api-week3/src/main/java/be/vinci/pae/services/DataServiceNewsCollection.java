package be.vinci.pae.services;

import be.vinci.pae.domain.News;

import java.util.List;

public interface DataServiceNewsCollection {

    News getNews(int id);

    List<News> getNewsList();
//    List<News> getNewsList(Parameters);

    News addNews(News news);

    int nextNewsId();

    News updateNews(News news);

    News deleteNews(int id);

}
