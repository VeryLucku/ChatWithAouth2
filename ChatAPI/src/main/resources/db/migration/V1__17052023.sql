CREATE TABLE chats (
    id uuid NOT NULL,
    name character varying(255) NOT NULL,
    author character varying(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE "chat-member" (
    chat_id uuid,
    member_name character varying(255),
    role character varying(255),
    FOREIGN KEY (chat_id)
                            REFERENCES chats (id) MATCH SIMPLE
                            ON UPDATE NO ACTION
                            ON DELETE NO ACTION

);

CREATE TABLE messages (
    id uuid NOT NULL,
    created_at timestamp without time zone,
    message character varying(255),
    username character varying(255),
    chat_id uuid,
    PRIMARY KEY (id),
    FOREIGN KEY (chat_id)
                        REFERENCES chats (id) MATCH SIMPLE
                        ON UPDATE NO ACTION
                        ON DELETE NO ACTION
)