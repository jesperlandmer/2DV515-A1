# 2DV515-A1 API

#### Run

To run the project, download docker:
* [Docker för Mac](https://docs.docker.com/docker-for-mac/install/#download-docker-for-mac)  
* [Docker för Windows](https://docs.docker.com/toolbox/toolbox_install_windows/)  
* [Docker för Ubuntu](https://www.docker.com/docker-ubuntu) samt [docker-compose](https://docs.docker.com/compose/install/)

Start the project with `docker-compose up -d --build`.
Visit `localhost:80`.

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
`http://localhost/api/ub`

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
`http://localhost:8080/api/ib`

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