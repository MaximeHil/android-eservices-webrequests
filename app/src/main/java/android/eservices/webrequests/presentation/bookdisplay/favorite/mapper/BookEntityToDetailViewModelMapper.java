package android.eservices.webrequests.presentation.bookdisplay.favorite.mapper;


public class BookEntityToDetailViewModelMapper {

    //TODO



    private String languageMapper(String input) {
        switch (input) {
            case "en":
                return "English";
            case "fr":
                return "French";
            default:
                return "Unknown (" + input + ")";
        }
    }
}
