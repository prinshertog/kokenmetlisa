# Build stage
FROM node:20-alpine AS build

WORKDIR /app

COPY package*.json ./
RUN npm install -g npm@11.6.4
RUN npm install

COPY . .
RUN npm run build

# Production stage
FROM node:20-alpine

WORKDIR /app

COPY package*.json ./
RUN npm install -g npm@11.6.4
RUN npm install --production

COPY --from=build /app/build ./build

EXPOSE 3000

CMD ["node", "build"]