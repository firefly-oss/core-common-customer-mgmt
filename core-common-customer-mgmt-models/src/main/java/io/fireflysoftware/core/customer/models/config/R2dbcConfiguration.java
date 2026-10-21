/*
 * Copyright 2025 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package io.fireflysoftware.core.customer.models.config;

import io.fireflysoftware.core.customer.interfaces.enums.AddressKind;
import io.fireflysoftware.core.customer.interfaces.enums.EmailKind;
import io.fireflysoftware.core.customer.interfaces.enums.Gender;
import io.fireflysoftware.core.customer.interfaces.enums.IndustryClassificationScheme;
import io.fireflysoftware.core.customer.interfaces.enums.MaritalStatus;
import io.fireflysoftware.core.customer.interfaces.enums.PartyKind;
import io.fireflysoftware.core.customer.interfaces.enums.PhoneKind;
import io.fireflysoftware.core.customer.interfaces.enums.RelationshipKind;
import io.fireflysoftware.core.customer.interfaces.enums.ProviderStatus;
import io.fireflysoftware.core.customer.interfaces.enums.ResidencyStatus;
import io.fireflysoftware.core.customer.interfaces.enums.StatusCode;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.postgresql.client.SSLMode;
import io.r2dbc.postgresql.codec.EnumCodec;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Wires the R2DBC PostgreSQL driver with the codec registrar for every Postgres
 * custom enum used by this service. Without this, the driver binds enum-typed
 * parameters as {@code character varying} and Postgres rejects the implicit cast
 * inside the {@code =} operator, even with the V3 {@code CREATE CAST … AS IMPLICIT}
 * migration in place.
 */
@Configuration
public class R2dbcConfiguration extends AbstractR2dbcConfiguration {

    @Value("${DB_HOST:localhost}")
    private String host;

    @Value("${DB_PORT:5432}")
    private int port;

    @Value("${DB_NAME:postgres}")
    private String database;

    @Value("${DB_USERNAME:postgres}")
    private String username;

    @Value("${DB_PASSWORD:postgres}")
    private String password;

    @Value("${DB_SSL_MODE:disable}")
    private String sslMode;

    @WritingConverter
    static class PartyKindConverter implements Converter<PartyKind, PartyKind> {
        @Override public PartyKind convert(PartyKind source) { return source; }
    }

    @WritingConverter
    static class GenderConverter implements Converter<Gender, Gender> {
        @Override public Gender convert(Gender source) { return source; }
    }

    @WritingConverter
    static class MaritalStatusConverter implements Converter<MaritalStatus, MaritalStatus> {
        @Override public MaritalStatus convert(MaritalStatus source) { return source; }
    }

    @WritingConverter
    static class ResidencyStatusConverter implements Converter<ResidencyStatus, ResidencyStatus> {
        @Override public ResidencyStatus convert(ResidencyStatus source) { return source; }
    }

    @WritingConverter
    static class StatusCodeConverter implements Converter<StatusCode, StatusCode> {
        @Override public StatusCode convert(StatusCode source) { return source; }
    }

    @WritingConverter
    static class AddressKindConverter implements Converter<AddressKind, AddressKind> {
        @Override public AddressKind convert(AddressKind source) { return source; }
    }

    @WritingConverter
    static class EmailKindConverter implements Converter<EmailKind, EmailKind> {
        @Override public EmailKind convert(EmailKind source) { return source; }
    }

    @WritingConverter
    static class PhoneKindConverter implements Converter<PhoneKind, PhoneKind> {
        @Override public PhoneKind convert(PhoneKind source) { return source; }
    }

    @WritingConverter
    static class ProviderStatusConverter implements Converter<ProviderStatus, ProviderStatus> {
        @Override public ProviderStatus convert(ProviderStatus source) { return source; }
    }

    @WritingConverter
    static class IndustryClassificationSchemeConverter implements Converter<IndustryClassificationScheme, IndustryClassificationScheme> {
        @Override public IndustryClassificationScheme convert(IndustryClassificationScheme source) { return source; }
    }

    @WritingConverter
    static class RelationshipKindConverter implements Converter<RelationshipKind, RelationshipKind> {
        @Override public RelationshipKind convert(RelationshipKind source) { return source; }
    }

    @Override
    protected List<Object> getCustomConverters() {
        List<Object> converters = new ArrayList<>();
        converters.add(new PartyKindConverter());
        converters.add(new GenderConverter());
        converters.add(new MaritalStatusConverter());
        converters.add(new ResidencyStatusConverter());
        converters.add(new StatusCodeConverter());
        converters.add(new AddressKindConverter());
        converters.add(new EmailKindConverter());
        converters.add(new PhoneKindConverter());
        converters.add(new ProviderStatusConverter());
        converters.add(new IndustryClassificationSchemeConverter());
        converters.add(new RelationshipKindConverter());
        return converters;
    }

    @Bean
    @Primary
    @Override
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .host(host)
                        .port(port)
                        .username(username)
                        .password(password)
                        .database(database)
                        .sslMode(SSLMode.valueOf(sslMode.toUpperCase()))
                        .codecRegistrar(EnumCodec.builder()
                                .withEnum("party_kind_enum", PartyKind.class)
                                .withEnum("gender_enum", Gender.class)
                                .withEnum("marital_status_enum", MaritalStatus.class)
                                .withEnum("residency_status_enum", ResidencyStatus.class)
                                .withEnum("status_code_enum", StatusCode.class)
                                .withEnum("address_kind_enum", AddressKind.class)
                                .withEnum("email_kind_enum", EmailKind.class)
                                .withEnum("phone_kind_enum", PhoneKind.class)
                                .withEnum("provider_status_enum", ProviderStatus.class)
                                .withEnum("industry_classification_scheme_enum", IndustryClassificationScheme.class)
                                .withEnum("party_relationship_kind_enum", RelationshipKind.class)
                                .build())
                        .build()
        );
    }
}
