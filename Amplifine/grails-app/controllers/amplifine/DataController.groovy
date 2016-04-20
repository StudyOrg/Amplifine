package amplifine

import amplifine.utils.SearchUtil

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

        long beginTime = System.currentTimeMillis()

        List resultList = SearchUtil.search("goods", pattern, LIST_LIMIT, offset)
        if (resultList.size() < LIST_LIMIT) {
            offset = -1
        }

        long totalTime = System.currentTimeMillis() - beginTime

        render(view: "index", model: [result: resultList, search: pattern, initialSearch: pattern, totalTime: totalTime, offset: offset])
    }
}
