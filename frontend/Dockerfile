# Production stage
FROM node:20-alpine

WORKDIR /app

# Copy package files and install production dependencies
COPY package*.json ./
RUN npm install --production

# Copy pre-built application from the local build directory
COPY ./build ./build

# Copy any additional files needed for production
COPY package.json .
#COPY .env /app/build/.env

EXPOSE 3000
ARG NODE_ENV
ARG ORIGIN
ARG BASE_URL_BACKEND
ARG BODY_SIZE_LIMIT
ENV NODE_ENV=$NODE_ENV
ENV ORIGIN=$ORIGIN
ENV BASE_URL_BACKEND=$BASE_URL_BACKEND
ENV BODY_SIZE_LIMIT=$BODY_SIZE_LIMIT

CMD ["node", "build"]
