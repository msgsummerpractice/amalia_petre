FROM node:24-alpine

WORKDIR /my-app

COPY certs/zscaler.crt /usr/local/share/ca-certificates/zscaler.crt
RUN cat /usr/local/share/ca-certificates/zscaler.crt >> /etc/ssl/certs/ca-certificates.crt \
    && apk add --no-cache ca-certificates \
    && update-ca-certificates
ENV NODE_EXTRA_CA_CERTS=/usr/local/share/ca-certificates/zscaler.crt

COPY package.json package-lock.json ./
RUN npm ci --legacy-peer-deps

COPY . .
RUN npm run build

RUN npm install -g http-server
EXPOSE 8080
CMD ["http-server", "dist/my-app/browser", "-p", "8080", "--proxy", "http://localhost:8080?"]
 