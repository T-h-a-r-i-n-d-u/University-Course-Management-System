# ---------- build stage ----------
FROM node:20-alpine AS build
WORKDIR /app

# Install deps with better layer caching
COPY package.json package-lock.json ./
RUN npm ci

# Copy source and build
COPY . .
# Set base href from build arg (defaults to '/')
ARG BASE_HREF=/
RUN npm run build -- --configuration production --base-href=${BASE_HREF}

# ---------- runtime stage ----------
FROM nginx:1.27-alpine

# Remove default site
RUN rm -f /etc/nginx/conf.d/default.conf

# Copy our nginx config
COPY nginx.conf /etc/nginx/nginx.conf

# Copy Angular dist output (wildcard handles your app name)
# If your dist path differs, update this line accordingly.
COPY --from=build /app/dist/*/browser /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
