docker login registry.gitlab.com
docker build --cache-from registry.gitlab.com/hitexglobal.com/webteam/haloga/halago_portal:latest --tag registry.gitlab.com/hitexglobal.com/webteam/haloga/halago_portal:latest .
docker push registry.gitlab.com/hitexglobal.com/webteam/haloga/halago_portal:latest
ssh -tt root@14.225.7.41 <<-'ENDSSH'
  cd /home/halago/portal
  docker pull registry.gitlab.com/hitexglobal.com/webteam/haloga/halago_portal:latest
  docker-compose up -d
ENDSSH