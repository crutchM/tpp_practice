FROM maven:latest
RUN git clone https://github.com/crutchM/tpp_practice.git
WORKDIR tpp_practice
ENV JDBC_URL='jdbc:mysql://192.168.0.111:6033/tpp' DB_USER='root' DB_PASSWD='my_secret_password'
RUN mvn package
ENTRYPOINT ["/bin/bash"]
CMD ["run.sh"]