FROM alpine:latest
WORKDIR /Docker
COPY ./package.json ./package.json
RUN apk add --update --no-cache --no-check-certificate nodejs npm && npm install
COPY ./index.js ./index.js
CMD ["node", "index.js"]