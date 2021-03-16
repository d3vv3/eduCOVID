# pull official base image
FROM node:14-buster

# set working directory
WORKDIR /app

# add app
COPY . .

# RUN apk add --no-cache python3 build-base gcc wget git

# install app dependencies
# RUN npm install yarn --global
RUN yarn --version
RUN yarn add react-scripts -g
RUN yarn add serve -g
RUN yarn install
RUN yarn build

# start
CMD ["npx", "serve",  "-s", "build"]
