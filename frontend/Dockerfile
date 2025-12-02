# Production stage
FROM node:20-alpine

WORKDIR /app

COPY package*.json ./
RUN npm install -g npm@11.6.4
RUN npm set-script prepare ""
RUN npm install --production

COPY ./build ./build

COPY package.json .

EXPOSE 3000

CMD ["node", "build"]
