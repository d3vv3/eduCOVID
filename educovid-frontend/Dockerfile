# pull official base image
FROM node:14-buster

# set working directory
WORKDIR /app

# add app
COPY . .

# RUN apk add --no-cache python3 build-base gcc wget git

# install app dependencies
# RUN npm install yarn --global
RUN npm install react-scripts -g
RUN npm install serve -g --silent
RUN npm install --silent
RUN npm run-script build

# start
CMD ["npx", "serve",  "-s", "build"]
