# build
FROM node:lts AS node
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build

# serve
FROM nginx:alpine
COPY --from=node /app/dist/frontend/browser /usr/share/nginx/html
COPY ./default.conf /etc/nginx/conf.d/default.conf
EXPOSE 4200
CMD ["nginx", "-g", "daemon off;"]
