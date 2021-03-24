# Stage 1
# FROM node:12-alpine as build-step
# RUN mkdir -p /app
# WORKDIR /app
# COPY ./phone-service /app
# COPY package.json /app
# COPY proxy.json /app
# RUN npm install -g @angular/cli @angular-devkit/build-angular
# RUN npm install
# RUN npm run build --prod
# EXPOSE 4200
#CMD ng serve --host 0.0.0.0 --prod --proxy-config proxy.json

# Stage 2
FROM nginx:1.17.1-alpine
COPY ./dist/phone-service/ /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 80
