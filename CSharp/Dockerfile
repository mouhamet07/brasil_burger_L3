# Multi-stage Dockerfile for ASP.NET Core app (suitable for Render)
# Build stage
FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
WORKDIR /src

# Copy everything and restore
COPY . ./
RUN dotnet restore

# Publish
RUN dotnet publish -c Release -o /app/publish

# Runtime stage
FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS runtime
WORKDIR /app

ENV PORT 8080
ENV ASPNETCORE_URLS=http://+:$PORT

COPY --from=build /app/publish ./
EXPOSE $PORT

# Run the first published dll found (works when project name is unknown)
CMD ["sh", "-c", "dotnet /app/$(ls /app | grep -m1 -E '\\.dll$')"]


