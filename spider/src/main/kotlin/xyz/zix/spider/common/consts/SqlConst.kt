package xyz.zix.spider.common.consts

const val GROUP_DAY_WORK_DURATION = """
    select `date`, duration_minute 
    from `work_check_in`
    group by `date`
"""

const val INSERT_WORK_CHECK_IN_ON_DP = """
            <script>
           insert into `work_check_in`(duration_minute,start,end,`date`)
           values 
           <foreach collection="li"  item="record" separator=",">
           (
               #{record.durationMinute}, #{record.start}, #{record.end}, #{record.date}
           )
           </foreach>
           on duplicate key update
            duration_minute = values(duration_minute),
            start = values(start),
            end = values(end)
            </script>
        """

class SqlConst {

    companion object {
        val POETRY_GROUP_BY_SQL = """
            select author, count(1) as count 
            from poetry
            group by author
        """

        const val SQL2 = ""

    }

}