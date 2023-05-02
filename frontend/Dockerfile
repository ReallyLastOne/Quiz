FROM mcr.microsoft.com/devcontainers/typescript-node:16-buster as build
RUN npm install -g @angular/cli@13.3.10
RUN mkdir -p home/app
COPY ./ ./home/app
WORKDIR /home/app
RUN npm install
RUN ng build

FROM nginx as runtime
COPY /nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /home/app/dist/frontend /usr/share/nginx/html
