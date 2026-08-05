FROM node:24-alpine

COPY certs/zscaler.crt /usr/local/share/ca-certificates/zscaler.crt
RUN apk add --no-cache ca-certificates && update-ca-certificates
ENV NODE_EXTRA_CA_CERTS=/usr/local/share/ca-certificates/zscaler.crt

COPY . .
RUN npm install --legacy-peer-deps
RUN npm run build --no-check-certificate
RUN npm install -g http-server
EXPOSE 8080
CMD ["http-server", "dist/my-app", "-p", "8080"]