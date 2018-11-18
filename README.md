# 2DV515-A1 API

#### API
`http://localhost/api`

```
{
    "_links": {
        "self": {
            "href": "http://localhost:8080/api"
        },
        "user-based": {
            "href": "http://localhost:8080/api/ub",
            "title": "Get user-based recommendations"
        },
        "item-based": {
            "href": "http://localhost:8080/api/ib",
            "title": "Get item-based recommendations"
        }
    }
}
```

#### User-Based
`http://localhost/api/ib`

```
{
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/ub",
            "title": "user-based"
        },
        "euclidean": {
            "href": "http://localhost:8080/api/ub/euclidean?_user=userName",
            "title": "Get movie recommendations for user using Euclidean"
        },
        "pearson": {
            "href": "http://localhost:8080/api/ub/pearson?_user=userName",
            "title": "Get movie recommendations for user using Pearson"
        }
    }
}
```

#### Item-Based
`http://localhost:8080/api/ub`

```
{
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/ib",
            "title": "item-based"
        },
        "euclidean": {
            "href": "http://localhost:8080/api/ib/euclidean?_user=userName",
            "title": "Get movie recommendations for user using Euclidean"
        },
        "pregenerate": {
            "href": "http://localhost:8080/api/ib/pregenerate",
            "title": "Generate data for item-based recommendations using Euclidean"
        }
    }
}
```