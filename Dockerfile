FROM maven:latest
RUN git clone https://github.com/crutchM/tpp_practice.git
WORKDIR tpp_practice
ENV JDBC_URL='jdbc:mysql://localhost:3306/tpp' DB_USER='root' DB_PASSWD='22334455'
RUN mvn package
ENTRYPOINT ["/bin/bash"]
CMD ["run.sh"]