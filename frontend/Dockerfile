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
COPY .env /app/build/.env

EXPOSE 3000
ENV NODE_ENV=production
ENV PORT=3000
ENV BASE_URL_BACKEND=http://kokenmetlisa-backend:8080
ENV ORIGIN=http://kokenmetlisa-frontend:3000
ENV VITE_BASE_URL_BACKEND=http://kokenmetlisa-backend:8080
ENV BODY_SIZE_LIMIT=1G

CMD ["node", "build"]