FROM mariadb:10.6

RUN sed -i -e"s/^bind-address\s*=\s*127.0.0.1/bind-address = 0.0.0.0/" /etc/mysql/my.cnf

ADD ./.docker/mysql/schema.sql /docker-entrypoint-initdb.d/schema.sql
RUN chown -R mysql:mysql /docker-entrypoint-initdb.d/
RUN chmod 755 /docker-entrypoint-initdb.d/schema.sql

CMD ["mysqld"]

EXPOSE 3306
