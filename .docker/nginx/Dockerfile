FROM centos:7

WORKDIR /app
EXPOSE 8080

ENV PATH /app/node_modules/.bin:$PATH

RUN printf "[nginx]\nname=nginx repo\nbaseurl=https://nginx.org/packages/centos/7/x86_64/\ngpgcheck=0\nenabled=1" > /etc/yum.repos.d/nginx.repo && \
    curl --silent --location https://rpm.nodesource.com/setup_16.x | bash - && \
    curl --silent --location https://dl.yarnpkg.com/rpm/yarn.repo | tee /etc/yum.repos.d/yarn.repo && \
    rpm --import https://dl.yarnpkg.com/rpm/pubkey.gpg && \
    yum install vim nginx nodejs yarn -y && \
    yum clean all

RUN mkdir -p /app/logs && \
    mkdir -p /var/log/nginx

RUN chmod 777 /var/cache/nginx && \
    chmod 777 /var/run && \
    chmod 777 /app/logs && \
    chmod 777 /var/log/nginx && \
    rm -rf /var/log/nginx/* && \
    rm -rf /var/cache/nginx/*

COPY ./.docker/nginx/nginx.conf /etc/nginx/nginx.conf
COPY ./.docker/nginx/default.conf /etc/nginx/conf.d/default.conf
COPY ./front-end ./

RUN yarn
RUN yarn build

CMD ["nginx", "-g", "daemon off;"]
