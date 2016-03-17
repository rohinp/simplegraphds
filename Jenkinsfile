stage 'Checkout'
node {
    git 'https://github.com/rohinp/simplegraphds.git'
    sh 'gradle build --info'
}

stage 'UnitTest'
node {
    sh 'gradle test'
}

stage 'DockerImage'
node {
    sh 'gradle distDocker'
}