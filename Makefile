up:
	@echo "Starting containers..."
	@docker-compose up -d

upf:
	@echo "Starting containers..."
	@docker-compose up -d --force-recreate --no-cache --build

down:
	@echo "Stopping containers..."
	@docker-compose down -v

logs:
	@echo "Following logs..."
	@docker-compose logs -f
