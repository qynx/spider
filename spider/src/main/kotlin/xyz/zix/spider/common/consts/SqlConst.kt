package xyz.zix.spider.common.consts


class SqlConst {

    companion object {
        val POETRY_GROUP_BY_SQL = """
    select author, count(1) as count 
    from poetry
    group by author
        """
    }
}