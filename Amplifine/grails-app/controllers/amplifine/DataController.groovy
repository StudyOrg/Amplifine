package amplifine

import amplifine.utils.SearchResult
import amplifine.utils.SearchUtil

import static amplifine.utils.SearchUtil.SearchAlgo.*

class DataController {

    public static final int LIST_LIMIT = 10

    def index() {
        render(view: "index")
    }

    def textSearch() {
        int offset = (params.offset ? Integer.parseInt(params.offset) : 0)
        String pattern = (String) params.search

        if (!pattern) {
            index()
            return
        }

        SearchUtil.SearchAlgo algo = LEVENSHTEIN
        String algoStr = params?.algorithm ?: "lev"
        switch (algoStr) {
            case "lev":
                algo = LEVENSHTEIN
                break
            case "jv":
                algo = JARO_WINKLER
                break
            case "lcs":
                algo = LCS
                break
        }

        long beginTime = System.currentTimeMillis()

        SearchResult result = SearchUtil.search("goods", pattern, LIST_LIMIT, offset, algo)
        if (result.returnList.size() < LIST_LIMIT) {
            offset = -1
        }

        long totalTime = System.currentTimeMillis() - beginTime

        render(view: "index", model: [result: result.returnList, search: result.realPattern, initialSearch: pattern, totalTime: totalTime, offset: offset])
    }
}
