package be.vinci.pae.domain;

public class NewsFactoryImpl implements NewsFactory {

    @Override
    public News getNews() {
        return new NewsImpl();
    }

}
