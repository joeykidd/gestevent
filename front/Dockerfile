FROM node:17-alpine3.14 As build
WORKDIR /app
COPY ./front/package.json ./
COPY ./front/package-lock.json ./
RUN npm install --production
COPY ./front/ .
RUN npm run build

FROM nginx:stable-alpine
COPY --from=build /app/build /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]