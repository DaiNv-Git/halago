module.exports = {
  apps : [{
    name: "halago-portal",
    script: "npm",
    args: "start",
    time: true,
    instances: 1,
    exec_mode: 'fork',
    autorestart: true,
    watch: false,
    max_memory_restart: '1G',
    env: {
      NODE_ENV: "production",
    },
  }]
}