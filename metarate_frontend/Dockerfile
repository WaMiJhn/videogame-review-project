# Use a base image with Node.js and npm
FROM node:14-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy package.json and package-lock.json to the container
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the app's source code to the container
COPY . .

# Build the React app
RUN npm run build

# Specify the runtime command
CMD ["npm", "start"]
