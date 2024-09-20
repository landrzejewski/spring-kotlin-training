Implement a personal blog application. Apply the acquired knowledge of Kotlin language and Spring framework.
Use the discussed payment example as a reference. Expose application logic using REST API.  Store data in the H2 database.

UseCases for article authors:
- create article
- delete article
- update article
- publish article

UseCases for article readers:
- add article like/dislike
- add article comment
- find articles by category
- find articles by tags

### Queries

findByCategory:
  select new pl.training.blog.application.ArticleView(a.id, a.title, a.author) from Articles a where a.category = :category

findByTags:
  select new pl.training.blog.application.ArticleView(a.id, a.title, a.author) from Articles a join a.tags t where t.name in :tags group by a having count (a) = :count

findByCategoryAndTags:  
  select new pl.training.blog.application.ArticleView(a.id, a.title, a.author) from Articles a join a.tags t where a.category = :category and t.name in :tags group by a having count (a) = :count
  


