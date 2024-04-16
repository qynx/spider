package xyz.zix.spider.common.consts

public val POETRY_BLOG_TEMPLATE = """
---
icon: alias
date: %s
category:
  - 诗词
tag:
  - 诗词
---

# %s

<!-- more -->

::: center
%s
::: 
""".trimIndent()

class BlogTemplateConst {
}