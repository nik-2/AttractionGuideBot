package by.nikita.attractions.util;

import by.nikita.attractions.constant.Constant;
import fastily.jwiki.core.Wiki;
import fastily.jwiki.dwrap.PageSection;
import lombok.SneakyThrows;
import okhttp3.HttpUrl;
import org.sweble.wikitext.engine.PageId;
import org.sweble.wikitext.engine.PageTitle;
import org.sweble.wikitext.engine.WtEngineImpl;
import org.sweble.wikitext.engine.config.WikiConfig;
import org.sweble.wikitext.engine.nodes.EngProcessedPage;
import org.sweble.wikitext.engine.utils.DefaultConfigEnWp;
import org.sweble.wikitext.example.TextConverter;

import java.util.List;

/**
 * The type Wiki worker.
 */
public class WikiWorker {
    /**
     * Gets info by wiki.
     *
     * @param cityName the city name
     * @return the info by wiki
     */
    public String getInfoByWiki(String cityName) {
        //retrieve a wiki
        Wiki wiki = new Wiki(HttpUrl.get(Constant.WIKI_ENDPOINT));
        StringBuilder stringBuilder = new StringBuilder();
        //retrieve a page sections


        List<PageSection> pageSections = wiki.splitPageByHeader(cityName);

        //check pageSections
        if (pageSections.isEmpty()) {
            return null;
        }

        //retrieve a categories
        List<String> categories = wiki.getCategoriesOnPage(cityName);

        //check got categories on need us category
        if (checkCategories(categories) == 0) {
            return null;
        }

        //retrieve wiki information from need us sections
        stringBuilder.append(retrieveWikiInformationFromSection(pageSections, stringBuilder));

        if (stringBuilder.length() == 0) {
            return retrieveAndCheckFirstParagraph(wiki, cityName, stringBuilder);
        } else {
            return convertAndCheckWikiText(cityName, stringBuilder.toString());
        }
    }

    private int checkCategories(List<String> categories){
        //check got categories on need us category
        int numberOfCoincidences = 0;
        for (String category : categories) {
            category = category.toLowerCase();
            if (category.contains("страницы с картами") || category.contains("города") || category.contains("остров")
                    || category.contains("полуостров") || category.contains("населённые пункты")) {
                numberOfCoincidences++;
                break;
            }
        }
        return numberOfCoincidences;
    }

    private StringBuilder retrieveWikiInformationFromSection(List<PageSection> pageSections, StringBuilder stringBuilder){
        //retrieve wiki information from need us sections
        for (PageSection pageSection : pageSections) {
            String header = pageSection.header;
            if (header != null) {
                header = header.toLowerCase();
                if (header.contains("достопримечательности") || header.contains("архитектура")
                        || header.contains("памятники") || header.contains("площадь") || header.contains("парки")
                        || header.contains("небоскрёбы") || header.contains("мосты") || header.contains("музеи")
                        || header.contains("досуг") || header.contains("культура")) {
                    stringBuilder.append(pageSection.text).append("\n");
                }
            }
        }
        return stringBuilder;
    }

    private String retrieveAndCheckFirstParagraph(Wiki wiki, String cityName, StringBuilder stringBuilder){
        //retrieve wiki information from first paragraph
        final int maxLength = 3890;
        stringBuilder.append("Мои ботодрузья из википедии не нашли информации об достопримечательностях этого города," +
                " но прислали краткую информацию о нём - пересылаю её тебе:\n\n").append(wiki.getTextExtract(cityName));
        //check text length
        if (stringBuilder.length() > maxLength) {
            stringBuilder.substring(0, maxLength);
        }
        return stringBuilder.toString();
    }

    private String convertAndCheckWikiText(String cityName, String text){
        //convert wiki text
        final int maxLength = 3890;
        String responseText = convertWikiText(cityName, text);
        responseText = responseText.replaceAll("-+\n", "");
        //check text length
        if (responseText.length() > maxLength) {
            responseText = responseText.substring(0, maxLength);
        }
        return responseText;
    }

    @SneakyThrows
    private String convertWikiText(String title, String wikiText) {
        int maxLength = 500;
        wikiText = wikiText.replaceAll(Constant.REGEX_FILE, "");
        wikiText = wikiText.replaceAll(Constant.REGEX_JPEG1, "");
        wikiText = wikiText.replaceAll(Constant.REGEX_JPEG2, "");
        // Set-up a simple wiki configuration
        WikiConfig config = DefaultConfigEnWp.generate();
        // Instantiate a compiler for wiki pages
        WtEngineImpl engine = new WtEngineImpl(config);
        // Retrieve a page
        PageTitle pageTitle = PageTitle.make(config, title);
        PageId pageId = new PageId(pageTitle, -1);
        // Compile the retrieved page
        EngProcessedPage engProcessedPage = engine.postprocess(pageId, wikiText, null);
        TextConverter textConverter = new TextConverter(config, maxLength);
        return (String) textConverter.go(engProcessedPage.getPage());
    }
}
