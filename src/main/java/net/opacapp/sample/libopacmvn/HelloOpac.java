package net.opacapp.sample.libopacmvn;

import de.geeksfactory.opacclient.OpacApiFactory;
import de.geeksfactory.opacclient.apis.OpacApi;
import de.geeksfactory.opacclient.objects.DetailledItem;
import de.geeksfactory.opacclient.objects.Library;
import de.geeksfactory.opacclient.objects.SearchRequestResult;
import de.geeksfactory.opacclient.searchfields.SearchField;
import de.geeksfactory.opacclient.searchfields.SearchQuery;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloOpac {

    public static String LIBRARY_NAME = "Stadtbibliothek Nürnberg";
    public static String LIBRARY_CONFIG = "{\"_active\":true,\"_notice_text\":null,\"_plus_required\":false,\"_plus_store_url\":null,\"_support_contract\":false,\"_version_required\":0,\"account_supported\":true,\"api\":\"adis\",\"city\":\"N\u00fcrnberg\",\"country\":\"Deutschland\",\"data\":{\"baseurl\":\"https://online-service2.nuernberg.de/aDISWeb/app\",\"startparams\":\"service=direct/0/Home/$DirectLink&sp=Sapp1%3A4103\"},\"geo\":[49.4518639,11.0829931],\"information\":\"http://www.nuernberg.de/internet/stadtbibliothek/\",\"library_id\":8743,\"state\":\"Bayern\",\"title\":\"Stadtbibliothek\"}";

    public static void main(final String[] args) throws JSONException, OpacApi.OpacErrorException, IOException {
        System.out.println("Hello OPAC!");

        // Create a library object
        Library library;
        library = Library.fromJSON(LIBRARY_NAME, new JSONObject(LIBRARY_CONFIG));

        // Instantiate the appropriate API class
        OpacApi api = OpacApiFactory.create(library, "HelloOpac/1.0.0");

        System.out.println("Obtaining search fields...");
        List<SearchField> searchFields = api.getSearchFields();

        for (int i = 0; i < searchFields.size(); i = i + 1) {
            System.out.println("Found search field #"+i+": " + searchFields.get(i)); // .getDisplayName());
            System.out.println("  -> is advanced: " + searchFields.get(i).isAdvanced());
        }
          

        List<SearchQuery> query = new ArrayList<SearchQuery>();
        query.add(new SearchQuery(searchFields.get(3), "Test")); //FIXME: input data end up in the wrong place
        System.out.println("Searching for 'Test' in this field...");

        SearchRequestResult searchRequestResult = api.search(query);
        System.out.println("Found " + searchRequestResult.getTotal_result_count() + " matches.");
        System.out.println("First match: " + searchRequestResult.getResults().get(0).toString());

        System.out.println("Fetching details for the first result...");

        //DetailledItem detailledItem = api.getResult(0);
        //System.out.println("Got details: " + detailledItem.toString());
    }
}
