# spring-login-tuto

Project developed with a mix of tutorials about authentication with Spring and JWT

Thanks to:
- [Amigos Code](https://www.youtube.com/watch?v=b9O9NI-RJ3o)
- [Ivana Soledad Rojas Córsico](https://www.youtube.com/watch?v=nwqQYCM4YT8)
- [dev.to](https://dev.to/abhi9720/a-comprehensive-guide-to-jwt-authentication-with-spring-boot-117p)


## Run

- Build docker image
```bash
docker build -t spring-jwt-img .
```

- Run image on container
```bash
docker run --name spring-jwt-cont --env-file ./.env -p 8080:8080 -d spring-jwt-img
```


## Maintainers

Just me, [Iván](https://github.com/Ivan-Montes) :sweat_smile:


## License

[GPLv3 license](https://choosealicense.com/licenses/gpl-3.0/)


---


[![Java](https://badgen.net/static/JavaSE/17/orange)](https://www.java.com/es/)
[![Maven](https://badgen.net/badge/icon/maven?icon=maven&label&color=red)](https://https://maven.apache.org/)
[![Spring](https://img.shields.io/badge/spring-blue?logo=Spring&logoColor=white)](https://spring.io)
[![GitHub](https://badgen.net/badge/icon/github?icon=github&label)](https://github.com)
[![Eclipse](https://badgen.net/badge/icon/eclipse?icon=eclipse&label)](https://https://eclipse.org/)
[![Docker](https://badgen.net/badge/icon/docker?icon=docker&label)](https://www.docker.com/)
[![GPLv3 license](https://badgen.net/static/License/GPLv3/blue)](https://choosealicense.com/licenses/gpl-3.0/)