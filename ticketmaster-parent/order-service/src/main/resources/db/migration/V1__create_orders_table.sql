CREATE TABLE orders (
                        id UUID PRIMARY KEY,
                        customer_id UUID NOT NULL,
                        ticket_id UUID NOT NULL,
                        total_amount NUMERIC(38, 2) NOT NULL,
                        status VARCHAR(50) NOT NULL,
                        created_at TIMESTAMP NOT NULL,
                        expires_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_orders_ticket_id ON orders(ticket_id);
CREATE INDEX idx_orders_customer_id ON orders(customer_id);