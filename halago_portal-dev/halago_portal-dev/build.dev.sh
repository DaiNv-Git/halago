docker login registry.gitlab.com
docker build --cache-from registry.gitlab.com/hitexglobal.com/webteam/haloga/halago_portal:dev --tag registry.gitlab.com/hitexglobal.com/webteam/haloga/halago_portal:dev .
docker push registry.gitlab.com/hitexglobal.com/webteam/haloga/halago_portal:dev
ssh -tt root@14.225.7.41 <<-'ENDSSH'
  cd /home/halago/dev
  docker pull registry.gitlab.com/hitexglobal.com/webteam/haloga/halago_portal:dev
  docker-compose -f docker-compose-dev.yml up -d
ENDSSH